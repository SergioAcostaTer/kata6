package software.ulpgc.kata6;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component
public class CommandFactory {
    private final Map<String, BiFunction<SpringRequestAdapter, SpringResponseAdapter, Command>> commands = new HashMap<>();


    public CommandFactory() {
        register("/api/password", CustomPasswordCommand::new);
    }


    public void register(String path, BiFunction<SpringRequestAdapter, SpringResponseAdapter, Command> constructor){
        commands.put(path, constructor);
    }


    public Command get(String path, SpringRequestAdapter request, SpringResponseAdapter response){
        BiFunction<SpringRequestAdapter, SpringResponseAdapter, Command> constructor = commands.get(path);
        if (constructor == null) throw new IllegalArgumentException("Not Exists");
        return constructor.apply(request, response);
    }

}
