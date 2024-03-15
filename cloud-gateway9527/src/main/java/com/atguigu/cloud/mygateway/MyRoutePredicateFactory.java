package com.atguigu.cloud.mygateway;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 需求说明，自定义配置会员等级userType，按照紧，银和yml配置等级，以适配是否访问
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config>{


    public MyRoutePredicateFactory(){
        super(MyRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
                if(userType==null){
                    return false;
                }
                if(userType.equalsIgnoreCase(config.getUserType())){
                    return true;
                }
                return false;
            }

        };
    }

    @Validated
    public static class Config{

        @Getter
        @Setter
        @NotEmpty
        private String userType;

    }
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("userType");
    }

}
