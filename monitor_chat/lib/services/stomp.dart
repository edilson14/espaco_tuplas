import 'dart:typed_data';

import 'package:flutter/foundation.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';

class StompClientService {
  var stompcClient;

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
        Uint8List? bynary = message.binaryBody;
        if (bynary != null) {
          if (kDebugMode) {
            print(String.fromCharCodes(bynary));
          }
        }
      },
    );
  }
}
