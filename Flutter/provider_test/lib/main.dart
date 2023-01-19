import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'myViewModel.dart';


// https://ithelp.ithome.com.tw/articles/10227927
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Text("");
  }
}

class PageA extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Text("");
  }
}

class PageB extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Page B"),),
      body: Center(
        child: Consumer<myViewModel>(builder: (context, counter, _) {
          return Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('目前計數值:'),
              Text('${counter.count}'),
            ],
          );
        }),
      ),
      floatingActionButton: FloatingActionButton(
          onPressed: () {
            Provider.of<myViewModel>(context, listen: false).addOne();
          },
      )
    );
  }
}