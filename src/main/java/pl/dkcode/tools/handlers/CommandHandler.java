package pl.dkcode.tools.handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import pl.dkcode.tools.ToolsPlugin;
import pl.dkcode.tools.handlers.enums.GroupType;
import pl.dkcode.tools.handlers.enums.User;
import pl.dkcode.tools.utils.ChatUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created: 17.02.2021
 * @Class: CommandHandler
 */

public class CommandHandler {

    public static final HashMap<String, Command> commands = new HashMap<>();
    private static final Reflection.FieldAccessor<SimpleCommandMap> field = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
    private static CommandMap cmdMap = CommandHandler.field.get(Bukkit.getServer().getPluginManager());

    public static void register(Command... cmds) {
        for(Command cmd : cmds) {
            if (CommandHandler.cmdMap == null) CommandHandler.cmdMap = CommandHandler.field.get(Bukkit.getServer().getPluginManager());
            CommandHandler.cmdMap.register(cmd.getName(), cmd);
            CommandHandler.commands.put(cmd.getName(), cmd);
        }
    }

    public static abstract class Command extends org.bukkit.command.Command {

        private final String name;
        private final GroupType perm;
        private final String usage;
        private final ToolsPlugin plugin;

        protected Command(String name, GroupType perm, String usage, List<String> aliases, ToolsPlugin plugin) {
            super(name,perm.getName(),usage,aliases);
            this.name = name;
            this.perm = perm;
            this.usage = usage;
            this.plugin = plugin;
        }

        public boolean execute(final CommandSender sender, String label, final String[] args) {
            Player p = (Player) sender;
            User u = UserHandler.get(p);
            if ((u == null && sender instanceof Player) || sender instanceof Player &&
                    perm.getLevel() > u.getGroup().getLevel()) {
                ChatUtil.sendMessage((Player) sender, "&cNie masz uprawnien do uzycia tej komendy!");
                return true;
            }
            return onExecute(sender, args);
        }

        public abstract boolean onExecute(final CommandSender sender, final String[] args);

        public String getName() {
            return name;
        }

        public GroupType getPerm() {
            return perm;
        }

        public String getUsage() {
            return usage;
        }

        public ToolsPlugin getPlugin() {
            return plugin;
        }

        public void doUsage(Player player){
            ChatUtil.sendMessage(player, "&dPoprawne uzycie: &f" + getUsage());
        }

    }


    public static final class Reflection {

        public static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType) {
            return getField(target, name, fieldType, 0);
        }

        private static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType, int index) {
            Field[] declaredFields;
            for (int length = (declaredFields = target.getDeclaredFields()).length, i = 0; i < length; ++i) {
                final Field field = declaredFields[i];
                if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                    field.setAccessible(true);
                    return new FieldAccessor<T>() {
                        @Override
                        public T get(final Object target) {
                            try {
                                return (T)field.get(target);
                            }
                            catch (IllegalAccessException e) {
                                throw new RuntimeException("Cannot access reflection.", e);
                            }
                        }

                        @Override
                        public void set(final Object target, final Object value) {
                            try {
                                field.set(target, value);
                            }
                            catch (IllegalAccessException e) {
                                throw new RuntimeException("Cannot access reflection.", e);
                            }
                        }

                        @Override
                        public boolean hasField(final Object target) {
                            return field.getDeclaringClass().isAssignableFrom(target.getClass());
                        }
                    };
                }
            }
            if (target.getSuperclass() != null) {
                return (FieldAccessor<T>)getField(target.getSuperclass(), name, (Class<Object>)fieldType, index);
            }
            throw new IllegalArgumentException("Cannot find field with type " + fieldType);
        }

        public static MethodInvoker getMethod(final Class<?> clazz, final String methodName, final Class<?>... params) {
            return getTypedMethod(clazz, methodName, null, params);
        }

        public static MethodInvoker getTypedMethod(final Class<?> clazz, final String methodName, final Class<?> returnType, final Class<?>... params) {
            Method[] declaredMethods;
            for (int length = (declaredMethods = clazz.getDeclaredMethods()).length, i = 0; i < length; ++i) {
                final Method method = declaredMethods[i];
                if (((methodName == null || method.getName().equals(methodName)) && returnType == null) || (method.getReturnType().equals(returnType) && Arrays.equals(method.getParameterTypes(), params))) {
                    method.setAccessible(true);
                    return (target, arguments) -> {
                        try {
                            return method.invoke(target, arguments);
                        }
                        catch (Exception e) {
                            throw new RuntimeException("Cannot invoke method " + method, e);
                        }
                    };
                }
            }
            if (clazz.getSuperclass() != null) {
                return getMethod(clazz.getSuperclass(), methodName, params);
            }
            throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
        }


        public interface FieldAccessor<T>
        {
            T get(final Object p0);

            void set(final Object p0, final Object p1);

            boolean hasField(final Object p0);
        }

        public interface MethodInvoker
        {
            Object invoke(final Object p0, final Object... p1);
        }
    }

}
