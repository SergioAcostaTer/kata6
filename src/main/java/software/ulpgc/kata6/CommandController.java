package software.ulpgc.kata6;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommandController {
    private final CommandFactory commandFactory;

    public CommandController(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }


    @GetMapping("/**")
    public ResponseEntity<String> handleRequest(HttpServletRequest request){
        SpringRequestAdapter requestAdapter = new SpringRequestAdapter(request);
        SpringResponseAdapter responseAdapter = new SpringResponseAdapter();

        String path = request.getRequestURI();
        Command command = commandFactory.get(path, requestAdapter, responseAdapter);
        command.execute();

        return ResponseEntity.ok(responseAdapter.getBody());
    }

}
