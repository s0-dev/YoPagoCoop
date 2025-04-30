import 'package:flutter/material.dart';

class PaymentScreen extends StatelessWidget {
  const PaymentScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const Color colorYellow = Color(0xFFCEAE00);
    const Color textColor = Color(0xFF0B0B0B);
    const Color primaryButton = Color(0xFFCEAE00);
    const Color progressColor = Color.fromARGB(255, 206, 191, 106);
    const double progress = 0.85; // ejemplo de progreso: 85%

    return Scaffold(
      appBar: AppBar(
        title: const Text('Pagos'),
        backgroundColor: colorYellow,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Card(
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
              elevation: 3,
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Row(
                      children: [
                        SizedBox(width: 6),
                        Text(
                          'Resumen de Pagos',
                          style: TextStyle(fontWeight: FontWeight.bold, fontSize: 25, color: textColor),
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),
                    const Text(
                      'Estado actual de tus pagos y cuotas',
                      style: TextStyle(color: Colors.grey, fontSize: 14),
                    ),
                    const SizedBox(height: 16),
                    const Text(
                      'Total pagado este año',
                      style: TextStyle(color: Colors.grey, fontSize: 14),
                    ),
                    const Text(
                      '\$16,000',
                      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 25),
                    ),
                    const SizedBox(height: 16),
                    const Text(
                      'Pagos pendientes',
                      style: TextStyle(color: Colors.grey, fontSize: 14),
                    ),
                    const Text(
                      '\$2,000',
                      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 25, color: Color.fromARGB(255, 232, 77, 66)),
                    ),
                    const SizedBox(height: 16),
                    const Text(
                      'Próximo vencimiento',
                      style: TextStyle(color: Colors.grey, fontSize: 14),
                    ),
                    const Text(
                      '10 de Julio, 2024',
                      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 25),
                    ),
                    const SizedBox(height: 16),
                    const Text(
                      'Progreso anual pagado',
                      style: TextStyle(color: Colors.grey, fontSize: 14),
                    ),
                    const SizedBox(height: 8),
                    Row(
                      children: [
                        Expanded(
                          child: ClipRRect(
                            borderRadius: BorderRadius.circular(12),
                            child: LinearProgressIndicator(
                              value: progress,
                              minHeight: 13,
                              backgroundColor: Colors.grey[350],
                              color: progressColor,
                            ),
                          ),
                        ),
                        const SizedBox(width: 10),
                        Text(
                          '${(progress * 100).toStringAsFixed(0)}%',
                          style: const TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold,
                            color: textColor,
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 20),
                  ],
                ),
              ),
            ),
            const SizedBox(height: 20),
            Card(
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
              elevation: 3,
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        const Text(
                          'Cuota mensual - Julio',
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 16,
                            color: Color(0xFF0B0B0B),
                          ),
                        ),
                        const Spacer(),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                          decoration: BoxDecoration(
                            color: Colors.blue[100],
                            borderRadius: BorderRadius.circular(20),
                          ),
                          child: const Text(
                            'Pendiente',
                            style: TextStyle(
                              fontSize: 12,
                              fontWeight: FontWeight.w500,
                              color: Colors.blue,
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 6),
                    const Text(
                      'Cuota mensual correspondiente al mes de Julio 2023',
                      style: TextStyle(color: Colors.grey, fontSize: 14),
                    ),
                    const SizedBox(height: 16),
                    Row(
                      children: const [
                        Text(
                          'Monto:',
                          style: TextStyle(color: Colors.grey),
                        ),
                        SizedBox(width: 231),
                        Text(
                          '\$2,000',
                          style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),
                    Row(
                      children: const [
                        Text(
                          'Vencimiento:',
                          style: TextStyle(color: Colors.grey),
                        ),
                        SizedBox(width: 85),
                        Icon(Icons.calendar_today, size: 16, color: Colors.grey),
                        SizedBox(width: 5),
                        Text(
                          '10 de Julio, 2023',
                          style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                        ),
                      ],
                    ),
                    const SizedBox(height: 20),
                    Align(
                      alignment: Alignment.centerRight,
                      child: ElevatedButton(
                        onPressed: () {
                          Navigator.pushNamed(context, 'pay');
                        },
                        style: ElevatedButton.styleFrom(
                          backgroundColor: primaryButton,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                        child: const Text(
                          'Pagar ahora',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                  ],
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
