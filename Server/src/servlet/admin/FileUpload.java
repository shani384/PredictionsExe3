package servlet.admin;

import engine.api.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import utils.ServletUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet("/admin/upload-file")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUpload extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        Collection<Part> parts = request.getParts();

        out.println("Total parts : " + parts.size());

        StringBuilder fileContent = new StringBuilder();

        for (Part part : parts) {
            printPart(part, out);
            //to write the content of the file to a string
            fileContent.append(readFromInputStream(part.getInputStream()));
        }
        Engine engine = ServletUtils.getEngine(getServletContext());
        engine.readWorldWithServer(fileContent);
        printFileContent(fileContent.toString(), out);
    }

    private void printPart(Part part, PrintWriter out) {
        StringBuilder sb = new StringBuilder();
        for (String header : part.getHeaderNames()) {
            sb.append(header).append(" : ").append(part.getHeader(header)).append("\n");
        }

        out.println(sb.toString());
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }

    private void printFileContent(String content, PrintWriter out) {
        out.println("File content:");
        out.println(content);
    }
}