import 'package:flutter/material.dart';

class SplashScreen extends StatelessWidget {
  const SplashScreen({super.key});

  @override
  Widget build(BuildContext context) {
    // First, define colors from your Figma design
    const Color backgroundColor = Color.fromARGB(255, 0, 0, 0);
    const Color primaryPurple = Color(0xFF9C88FF);
    const Color buttonColor = Color(0xFF432861);
    const Color textColor = Color(0xFFEEEEEE);
    const Color textColorSecundary = Color(0xFFCEAE00);
    return Scaffold(
      backgroundColor: backgroundColor,
      body: SafeArea(
        child: Column(
          children: [
            // Content at the top
            Expanded(
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20.0),
                  child: Column(
                  
                    children: [
                      // Logo centered at the top
                      // Uncomment and provide the correct path to your image
                      Image.asset(
                        'lib/assets/images/YPC_logo.png',
                        width: 250,
                        height: 250,
                      ),
                      
                      const SizedBox(height: 8),

                      // Main Title
                      Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          for (final line in const [
                            'El espacio digital',
                            'para ver como se',
                            'gestionan los recursos de todos.'
                          ])
                            SizedBox(
                              width: 300, // Ancho máximo deseado
                              child: Text(
                                line,
                                textAlign: TextAlign.start, // Alineación izquierda dentro del espacio centrado
                                style: const TextStyle(
                                  fontSize: 32,
                                  fontFamily: 'Montserrat',
                                  fontWeight: FontWeight.w300,
                                  color: textColor,
                                ),
                              ),
                            ),
                        ],
                      ),

                      const SizedBox(height: 70), 

                      // Subtitle
                      const Text(
                        'Información\n'
                        'Acciones\n'
                        'Transparencia\n',
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: 36, 
                          fontFamily: 'Montserrat',
                          fontWeight: FontWeight.w700,
                          color: textColorSecundary
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),

            // Content at the bottom
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
                        Navigator.pushNamed(context, 'login');
                      },
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center, // Centra el contenido
                        children: [
                          Image.asset(  // Usa Image.asset directamente
                            'lib/assets/images/VectorSumate.png',
                            width: 20,
                            height: 20,
                            color: textColor,  // Opcional: aplica color si es necesario
                          ),
                          SizedBox(width: 8), // Espacio entre icono y texto
                          const Text(
                            'Sumate',
                            style: TextStyle(fontSize: 16, color: textColor),
                          ),
                        ],
                      ),
                    ),
                  ),

                  // ===== ELIMINAR ESTE BOTON, SOLO PARA TESTS =====
                  const SizedBox(height: 16),

                  // "No tienes cuenta? Regístrate"
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Text(
                        'No tienes una cuenta? ',
                        style: TextStyle(color: Colors.white70),
                      ),
                      GestureDetector(
                        onTap: () {
                          // Navigate to your registration screen
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