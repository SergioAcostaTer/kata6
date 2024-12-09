package software.ulpgc.kata6;

import java.security.SecureRandom;

public class CustomPasswordCommand implements Command{
    private final SpringRequestAdapter request;
    private final SpringResponseAdapter response;

    public CustomPasswordCommand(SpringRequestAdapter request, SpringResponseAdapter response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() {
        String password = generatePassword();
        response.setBody(String.format("{\"password\":\"%s\"", password));
    }

    private String generatePassword() {
        int length = Integer.parseInt(request.getQueryParam("length").orElse("8"));

        String baseCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String specialCharacters = "!@#$%^&*()-_=+[]{}|;:,.<>?/";

        boolean includeSpecial = request.getBooleanQueryParam("special", true);

        String characters = includeSpecial ? baseCharacters + specialCharacters : baseCharacters;

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            passwordBuilder.append(characters.charAt(randomIndex));
        }

        return passwordBuilder.toString();
    }
}
