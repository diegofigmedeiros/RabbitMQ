package com.diegofigmedeiros.mensageria;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumidor {
    public static void main(String[] args) throws Exception {
        System.out.println("Consumidor");

        String NOME_FILA = "PROGDIST_2022";

        //criando a fabrica de conexoes e criando uma conexao
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("Admin123XX_");

        Connection conexao = connectionFactory.newConnection();

        //criando um canal e declarando uma fila
        Channel canal = conexao.createChannel();
        canal.queueDeclare(NOME_FILA, false, false, false, null);

        //Definindo a funcao callback
        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody());
            System.out.println("Recebi a mensagem: " + mensagem);
        };

        //Consome da fila
        canal.basicConsume(NOME_FILA, true, callback, consumerTag -> {});
        System.out.println("Continuarei executando outras atividades enquanto não chega mensagem...");
    }
}

