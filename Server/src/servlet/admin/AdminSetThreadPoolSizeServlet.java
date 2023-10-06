package servlet.admin;

import DTOManager.impl.MyThreadInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import engine.api.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.UserManager;
import utils.ServletUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/admin/update_threads_size")
public class AdminSetThreadPoolSizeServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type to JSON
        response.setContentType("application/json;charset=UTF-8");

        // Parse the JSON request body
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Parse the JSON request body into a JsonObject
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(requestBody.toString()).getAsJsonObject();

        // Get the new thread pool size from the JsonObject
        int newThreadPoolSize = jsonObject.get("threadPoolSize").getAsInt();

        // Update the thread pool size on the server
        boolean success = updateThreadPoolSize(newThreadPoolSize);

        // Prepare the response
        JsonObject jsonResponse = new JsonObject();
        if (success) {
            jsonResponse.addProperty("message", "Thread pool size updated successfully.");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            jsonResponse.addProperty("message", "Failed to update thread pool size.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        // Send the response
        response.getWriter().write(jsonResponse.toString());
    }


    // Simulate updating the thread pool size on the server
    private synchronized boolean updateThreadPoolSize(int newThreadPoolSize) {
        try{
            ServletUtils.getEngine(getServletContext()).setThreadPoolSize(newThreadPoolSize);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
