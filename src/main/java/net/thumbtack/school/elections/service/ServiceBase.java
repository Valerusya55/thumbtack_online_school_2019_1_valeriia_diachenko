package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.exeption.ServerException;

public class ServiceBase {
    private static Gson gson = new Gson();

    protected static <T> T classFromJson(String json, Class<T> classRequest) throws ServerException {
        try {
            return gson.fromJson(json, classRequest);
        } catch (JsonSyntaxException ex) {
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
    }
}
