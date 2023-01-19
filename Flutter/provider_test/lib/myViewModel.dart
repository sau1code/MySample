
import 'package:flutter/cupertino.dart';

class myViewModel with ChangeNotifier {
  int _count = 0;

  get count => _count;

  addOne() {
    _count++;
    notifyListeners();
  }
}