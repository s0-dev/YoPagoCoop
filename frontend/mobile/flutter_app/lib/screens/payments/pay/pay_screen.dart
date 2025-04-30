import 'package:flutter/material.dart';

class PayScreen extends StatelessWidget {
  const PayScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const Color colorYellow = Color(0xFFCEAE00);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Realizar Pago'),
        backgroundColor: colorYellow,
      ),
    );
  }
}