package br.com.karol.sistema.config;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.karol.sistema.builder.UsuarioTestFactory;
import br.com.karol.sistema.domain.enums.UserRole;

public class RolesInvocationContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod()
            .map(method -> method.isAnnotationPresent(ContextualizeUsuarioTypeWithRoles.class))
            .orElse(false);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        var rolesAnnotation = context.getRequiredTestMethod().getAnnotation(ContextualizeUsuarioTypeWithRoles.class);
        return Stream.of(rolesAnnotation.roles())
            .map(role -> new RolesTestTemplateInvocationContext(role));
    }

    static class RolesTestTemplateInvocationContext implements TestTemplateInvocationContext {
        private final String role;

        RolesTestTemplateInvocationContext(String role) {
            this.role = role;
        }

        @Override
        public String getDisplayName(int invocationIndex) {
            return String.format("role=%s", role);
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return List.of(new BeforeEachCallback() {

                @Override
                public void beforeEach(ExtensionContext context) {
                    var user = UsuarioTestFactory.getUsuarioAdmin();
                    user.setRole(UserRole.fromString(role));
                    SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
                }
            });
        }
    }
}
