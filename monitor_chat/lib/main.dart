import 'package:flutter/material.dart';
import 'package:monitor_chat/services/stomp.dart';
import 'package:get/get.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Monitoramento de Chat'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  StompClientService client = StompClientService();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Obx(
          () => ListView.builder(
            itemCount: client.mensagens.length,
            itemBuilder: (BuildContext context, index) {
              return ListTile(
                leading: Text(
                  client.mensagens[index].sender,
                  style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                  ),
                ),
                title: Text(
                  'Mensagem - ${client.mensagens[index].content}',
                ),
                subtitle: Text('Para - ${client.mensagens[index].reciever}'),
              );
            },
          ),
        ),
      ),
    );
  }
}
