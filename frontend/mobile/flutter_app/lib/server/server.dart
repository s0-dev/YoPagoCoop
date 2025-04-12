// lib/server/server.dart
class User {
  final String username;
  final String password;

  User({required this.username, required this.password});
}

class AuthService {
  // Simulación de una "base de datos" en memoria
  static List<User> _users = [];

  // Registrar usuario
  static Future<String?> register(String username, String password) async {
    await Future.delayed(Duration(seconds: 1)); // Simular latencia
    final exists = _users.any((user) => user.username == username);
    if (exists) {
      return 'El usuario ya existe';
    }
    _users.add(User(username: username, password: password));
    return null; // null = todo bien
  }

  // Login de usuario
  static Future<String?> login(String username, String password) async {
    await Future.delayed(Duration(seconds: 1)); // Simular latencia
    final user = _users.firstWhere(
      (u) => u.username == username && u.password == password,
      orElse: () => User(username: '', password: ''),
    );

    if (user.username == '') {
      return 'Usuario o contraseña incorrectos';
    }
    return null; // null = login exitoso
  }
}
