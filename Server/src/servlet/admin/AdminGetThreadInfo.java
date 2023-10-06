package servlet.admin;

import DTOManager.impl.MyThreadInfo;
import com.google.gson.Gson;
import engine.api.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.UserManager;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/admin/get_threads_info")
public class AdminGetThreadInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8"); // Set content type to JSON

        try (PrintWriter out = response.getWriter()) {
            Engine engine = ServletUtils.getEngine(getServletContext());
            Gson gson = ServletUtils.getGson(getServletContext());

            // Serialize the thread pool info to JSON and write it to the response
            String threadInfoJson = gson.toJson(engine.getThreadPoolInfo());
            out.print(threadInfoJson);
        } catch (IOException e) {
            // Handle any IOException that might occur during writing to the response
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
