package astue;

import astue.model.Device;
import astue.service.ModbusAgent;
import astue.service.ModbusAgentFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfiguration {

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ModbusAgentFactory getModbusFactory(){
        return new ModbusAgentFactory();
    }

}
