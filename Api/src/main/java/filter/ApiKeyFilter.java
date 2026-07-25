package filter;

import com.sipentas.Api.repository.ApiKeyRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, jakarta.servlet.ServletException {

        String path = request.getRequestURI();

        // ✅ Endpoint yang TIDAK butuh API Key
        if (path.startsWith("/api/users/register") ||
                path.startsWith("/api/users/login") ||
                path.startsWith("/api/register-app")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🔑 Ambil API Key dari header
        String apiKey = request.getHeader("X-API-Key");

        // 🔑 Validasi API Key
        if (apiKey == null || !apiKeyRepository.existsByApiKey(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false,\"message\":\"API Key tidak valid\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}