package servlet.regular;

import DTOManager.impl.WorldDTO;
import engine.api.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/simulations/details")
public class GetSimulationsDetails extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()){
            Engine engine = ServletUtils.getEngine(getServletContext());
            Map<String, WorldDTO> worldsDTO = engine.getWorldsDTO();
            String json = ServletUtils.getGson(getServletContext()).toJson(worldsDTO);
            out.println(json);
            out.flush();
        }
    }







}
