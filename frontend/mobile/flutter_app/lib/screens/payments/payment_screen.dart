import 'package:flutter/material.dart';

class PaymentScreen extends StatelessWidget {
  const PaymentScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const Color backgroundColor = Color(0xFFEEEEEE);
    const Color colorYellow = Color(0xFFCEAE00);
    const Color textColor = Color(0xFF0B0B0B);
    const Color textColorSecundary = Color(0xFFEEEEEE);
    
    return Scaffold(
      appBar: AppBar(
        title: const Text('Realizar Pago'),
        backgroundColor: colorYellow,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const SizedBox(height: 20),
            Text(
              '\$2000',
              style: TextStyle(
                fontSize: 48,
                fontWeight: FontWeight.bold,
                color: Colors.black87,
              ),
            ),
            const SizedBox(height: 10),
            const Text(
              'Fecha de vencimiento: 01/03/25',
              style: TextStyle(fontSize: 18, color: Colors.grey),
            ),
            const SizedBox(height: 30),
            const Text(
              'Selecciona método de pago:',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.w500),
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                PaymentMethodButton(icon: Icons.credit_card, label: 'Tarjeta'),
                PaymentMethodButton(icon: Icons.account_balance, label: 'Transferencia'),
                PaymentMethodButton(icon: Icons.money, label: 'Efectivo'),
              ],
            ),
            const SizedBox(height: 10),
            TextField(
                    style: const TextStyle(color: Colors.white54),
                    decoration: InputDecoration(
                      labelText: 'Ingrese la cantidad a pagar',
                      labelStyle: const TextStyle(color: textColorSecundary),
                      hintText: 'Mínimo \$2000 pesos',
                      hintStyle: const TextStyle(color: Color.fromARGB(133, 200, 184, 184)),
                      filled: true,
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
            Image.asset(
              'lib/assets/images/payments.png',
              width: 250,
              height: 250,
            ),
            const Spacer(),
            SizedBox(
              width: double.infinity,
              height: 50,
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor: colorYellow,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                ),
                onPressed: () {
                  // Aquí pones la lógica para realizar el pago
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Pago realizado con éxito!')),
                  );
                },
                child: const Text(
                  'Pagar ahora',
                  style: TextStyle(fontSize: 20, color: textColor),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class PaymentMethodButton extends StatelessWidget {
  final IconData icon;
  final String label;

  const PaymentMethodButton({
    required this.icon,
    required this.label,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        CircleAvatar(
          radius: 28,
          backgroundColor: Colors.amber[200],
          child: Icon(icon, size: 30, color: Colors.black87),
        ),
        const SizedBox(height: 6),
        Text(label, style: const TextStyle(fontSize: 14)),
      ],
    );
  }
}
