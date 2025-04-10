import 'package:flutter/material.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _obscureText = true;

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // Colors from your Figma design
    const Color backgroundColor = Color.fromARGB(255, 0, 0, 0); // Dark background
    const Color buttonColor = Color(0xFF432861); // Button color
    const Color primaryPurple = Color(0xFF432861); // Purple accent
    const Color textColor = Color(0xFFEEEEEE); // Text color
    

    return Scaffold(
      backgroundColor: backgroundColor,
      appBar: AppBar(
        backgroundColor: backgroundColor,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start, // Distribute space
          children: [
            // Content above the button
            SingleChildScrollView(
              padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [

                  Image.asset(
                        'lib/assets/images/secureLogin.png',
                        width: 250,
                        height: 200,
                  ),


                  // Title
                  const Text(
                    'Hola, otra vez!',
                    style: TextStyle(
                      fontSize: 32,
                      fontWeight: FontWeight.bold,
                      color: textColor,
                    ),
                  ),
                  const SizedBox(height: 8),

                  // Subtitle
                  const Text(
                    'Bienvenido de vuelta, ingresa tus datos para iniciar sesión',
                    style: TextStyle(fontSize: 16, color: textColor),
                  ),
                  const SizedBox(height: 32),

                  // Email Field
                  TextField(
                    controller: _emailController,
                    style: const TextStyle(color: textColor),
                    decoration: InputDecoration(
                      labelText: 'Email',
                      labelStyle: const TextStyle(color: textColor),
                      filled: true, // Esto activa el fondo
                      fillColor: Color.fromARGB(255, 28, 29, 28),
                      enabledBorder: OutlineInputBorder(
                        borderSide: const BorderSide(color: backgroundColor),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      focusedBorder: OutlineInputBorder(
                        borderSide: const BorderSide(
                          color: backgroundColor,
                          width: 2,
                        ),
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),

                  
                  // Password Field
                  TextField(
                    controller: _passwordController,
                    style: const TextStyle(color: textColor),
                    obscureText: _obscureText,
                    decoration: InputDecoration(
                      labelText: 'Contraseña',
                      labelStyle: const TextStyle(color: textColor),
                      hintText: 'Tu contraseña',
                      hintStyle: const TextStyle(color: Colors.white54),
                      filled: true, // Esto activa el fondo
                      fillColor: Color.fromARGB(255, 28, 29, 28),
                      suffixIcon: IconButton(
                        icon: Icon(
                          _obscureText
                              ? Icons.visibility_off
                              : Icons.visibility,
                          color: backgroundColor,
                        ),
                        onPressed: () {
                          setState(() {
                            _obscureText = !_obscureText;
                          });
                        },
                      ),
                      enabledBorder: OutlineInputBorder(
                        borderSide: const BorderSide(color: backgroundColor),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      focusedBorder: OutlineInputBorder(
                        borderSide: const BorderSide(
                          color: backgroundColor,
                          width: 2,
                        ),
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                  ),
                  const SizedBox(height: 170),
                ],
              ),
            ),
            
            // Grouping the button and the registration text together
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 16),
              child: Column(
                children: [
                  // "Iniciar sesión" Button
                  SizedBox(
                    width: double.infinity,
                    height: 48,
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: buttonColor,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(24),
                        ),
                      ),
                      onPressed: () {
                        // Implement login logic
                      },
                      child: const Text(
                        'Iniciar sesión',
                        style: TextStyle(fontSize: 16, color: Colors.white),
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),

                  // "No tienes una cuenta? Regístrate"
                  Row(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: [
                      const Text(
                        'No tienes una cuenta?',
                        style: TextStyle(color: Colors.white70),
                      ),
                      GestureDetector(
                        onTap: () {
                          // Navigate to register
                          Navigator.pushNamed(context, 'register');
                        },
                        child: Text(
                          'Regístrate',
                          style: TextStyle(
                            color: primaryPurple,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}