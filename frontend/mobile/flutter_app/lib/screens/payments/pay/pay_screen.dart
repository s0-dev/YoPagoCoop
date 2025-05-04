import 'package:flutter/material.dart';

class PayScreen extends StatefulWidget {
  const PayScreen({Key? key}) : super(key: key);

  @override
  State<PayScreen> createState() => _PayScreenState();
}

class _PayScreenState extends State<PayScreen> {
  String metodoPago = 'MercadoPago';
  final TextEditingController _montoController =
      TextEditingController(text: "\$2,000");

  @override
  Widget build(BuildContext context) {
    const Color colorYellow = Color(0xFFCEAE00);
    const Color textColor = Color(0xFF0B0B0B);
    const Color buttonColor = Color(0xFF432861);

    return Scaffold(
      appBar: AppBar(
        title: Text("Realizar pago"),
        centerTitle: true,
        elevation: 0,
        backgroundColor: colorYellow,
        foregroundColor: textColor,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              "Selecciona un método de pago para abonar Cuota mensual - Julio",
              style: TextStyle(color: Colors.grey[600]),
            ),
            SizedBox(height: 20),
            Text("Monto a pagar",
                style: TextStyle(fontWeight: FontWeight.bold, color: textColor)),
            SizedBox(height: 5),
            TextFormField(
              controller: _montoController,
              keyboardType: TextInputType.number,
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                isDense: true,
              ),
            ),
            SizedBox(height: 20),
            Text("Método de pago", style: TextStyle(fontWeight: FontWeight.bold)),
            SizedBox(height: 5),
            Text("Selecciona cómo deseas realizar el pago",
                style: TextStyle(color: Colors.grey[600], fontSize: 13)),
            SizedBox(height: 10),
            RadioListTile<String>(
              value: 'MercadoPago',
              groupValue: metodoPago,
              title: Text('MercadoPago'),
              secondary: Icon(Icons.payment),
              onChanged: (value) {
                setState(() {
                  metodoPago = value!;
                });
              },
            ),
            RadioListTile<String>(
              value: 'Transferencia bancaria',
              groupValue: metodoPago,
              title: Text('Transferencia bancaria'),
              secondary: Icon(Icons.account_balance),
              onChanged: (value) {
                setState(() {
                  metodoPago = value!;
                });
              },
            ),
            Divider(height: 30),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text("Total a pagar:", style: TextStyle(fontWeight: FontWeight.bold)),
                Text("\$2,000",
                    style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
              ],
            ),
            Text("Incluye todos los cargos", style: TextStyle(color: Colors.grey)),
            SizedBox(height: 20),
            Align(
              alignment: Alignment.center,
              child: Image.asset(
                'lib/assets/images/payments.png',
                width: 250,
                height: 250,
              ),
            ),
            SizedBox(height: 40),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () {
                  // Acción al presionar
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: buttonColor,
                  padding: EdgeInsets.symmetric(vertical: 16),
                ),
                child: Text(
                  "Continuar con el pago",
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
