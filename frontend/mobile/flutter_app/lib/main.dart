import 'package:flutter/material.dart';

import 'screens/start/splash_screen.dart';
import 'screens/auth/login_screen.dart';
import 'screens/auth/register_screen.dart';
import 'screens/home/home_screen.dart';
import 'screens/payments/payment_screen.dart';
import 'screens/payments/information_screen.dart';
import 'screens/payments/pay/pay_screen.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        fontFamily: 'Montserrat',
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const SplashScreen(),
        'login': (context) => const LoginScreen(),
        'register': (context) => const RegisterScreen(),
        'payment': (context) => const PaymentScreen(),
        'information': (context) => InfoPaymentsScreen(),
        'pay': (context) => PayScreen(),
        'home': (context) {
          final nombre = ModalRoute.of(context)!.settings.arguments as String;
          return HomeScreen(username: nombre);
        },
        
      },
    );
  }
}
