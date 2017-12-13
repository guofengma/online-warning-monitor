package com.carrying.coder.example;

import com.carrying.coder.message.IMessageSender;
import com.carrying.coder.message.MessageType;
import com.carrying.coder.message.TxtMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run( ExampleApplication.class, args );
    }

    @Bean
    public CommandLineRunner commandLineRunner(IMessageSender sender) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                Thread.sleep( 2000 );
                sender.send( TxtMessage.builder().txtMsg( "test" ).type( MessageType.SERVER_DOWN ).build() );
            }
        };
    }

}
