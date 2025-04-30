import 'package:flutter/material.dart';

class InfoPaymentsScreen extends StatelessWidget {
  final List<Map<String, dynamic>> pagosRealizados = [
    {'fecha': '01/03/25', 'monto': 2000},
    {'fecha': '01/02/25', 'monto': 2000},
    {'fecha': '01/01/25', 'monto': 2000},
  ];

  InfoPaymentsScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const Color colorSkyblue = Color(0xFF3D749C);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Pagos Realizados'),
        backgroundColor: colorSkyblue,
      ),
      body: ListView.builder(
        itemCount: pagosRealizados.length,
        itemBuilder: (context, index) {
          final pago = pagosRealizados[index];
          return ListTile(
            leading: const Icon(Icons.check_circle, color: Colors.green),
            title: Text('Fecha: ${pago['fecha']}'),
            subtitle: Text('Monto: \$${pago['monto']}'),
            trailing: const Text(
              'Pagado',
              style: TextStyle(
                color: Colors.green,
                fontWeight: FontWeight.bold,
              ),
            ),
          );
        },
      ),
    );
  }
}
