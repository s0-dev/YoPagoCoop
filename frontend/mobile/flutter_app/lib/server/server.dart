class User {
  final String nombre;
  final String username;
  final String password;

  User({required this.nombre, required this.username, required this.password});
}

class AuthService {
  static List<User> _users = [];

  static Future<String?> register(String nombre, String username, String password) async {
    await Future.delayed(Duration(seconds: 1));
    final exists = _users.any((user) => user.username == username);
    if (exists) {
      return 'El usuario ya existe';
    }
    _users.add(User(nombre: nombre, username: username, password: password));
    return null;
  }

  static Future<User?> login(String username, String password) async {
    await Future.delayed(Duration(seconds: 1));
    final user = _users.firstWhere(
      (u) => u.username == username && u.password == password,
      orElse: () => User(nombre: '', username: '', password: ''),
    );

    if (user.username == '') {
      return null;
    }
    return user;
  }

}
