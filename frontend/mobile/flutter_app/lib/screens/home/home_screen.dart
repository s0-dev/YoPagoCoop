// lib/screens/home/home_screen.dart
import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  final String username;

  const HomeScreen({super.key, required this.username});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Bienvenido, $username'),
      ),
      body: Center(
        child: Text(
          '¡Has iniciado sesión correctamente!',
          style: TextStyle(fontSize: 18),
        ),
      ),
    );
  }
}
