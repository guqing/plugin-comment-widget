package run.halo.comment.widget.captcha;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import run.halo.app.core.extension.endpoint.CustomEndpoint;
import run.halo.app.extension.GroupVersion;

@Component
@RequiredArgsConstructor
public class CaptchaEndpoint implements CustomEndpoint {

    private final CaptchaManager captchaManager;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
            .GET("captcha/-/generate", this::generateCaptcha)
            .build();
    }

    private Mono<ServerResponse> generateCaptcha(ServerRequest request) {
        return captchaManager.generate(request.exchange())
            .flatMap(captcha -> ServerResponse.ok().bodyValue(captcha.imageBase64()));
    }

    @Override
    public GroupVersion groupVersion() {
        return GroupVersion.parseAPIVersion("api.commentwidget.halo.run/v1alpha1");
    }
}
