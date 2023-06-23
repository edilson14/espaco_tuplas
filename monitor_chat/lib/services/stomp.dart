import 'package:get/get.dart';

import 'package:flutter/foundation.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';

class Messages {
  String sender;
  String reciever;
  String content;

  Messages(
      {required this.sender, required this.content, required this.reciever});
}

class StompClientService {
  var stompcClient;
  RxList<Messages> mensagens = <Messages>[].obs;

  StompClientService() {
    stompcClient = StompClient(
      config: StompConfig(
        url: 'ws://localhost:61614/stomp',
        onConnect: onConnect,
        onWebSocketError: (error) {
          print('algo deu errado $error');
        },
      ),
    );

    stompcClient.activate();
  }

  onConnect(StompFrame frame) {
    print("conectado ao Topico");
    stompcClient.subscribe(
      destination: "/topic/mensagens-suspeitas",
      callback: (StompFrame message) {
        if (message.binaryBody != null) {
          String mensagem = String.fromCharCodes(message.binaryBody!);
          _handleMessage(mensagem);
        }
      },
    );
  }

  _handleMessage(String mensagem) {
    mensagem = mensagem.replaceAll('{', '').replaceAll('}', '');
    List<String> mensagensSeparadas = mensagem.split(',');
    Messages message = Messages(
      sender: mensagensSeparadas[0].substring(7),
      content: mensagensSeparadas[1].substring(9),
      reciever: mensagensSeparadas[2].substring(10),
    );
    mensagens.add(message);
  }
}
