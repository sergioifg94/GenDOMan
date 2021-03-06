# GenDOMan

GenDOMan is a tool that generates the JavaScript code to create the nodes of a given HTML code

## Installation

Clone the repository, cd into the directory and execute `$ ./install.sh <installation path>`

## Usage

A simple example of usage is:

```
$ gendoman -i input.html
```

By default, GenDOMan generates ES6 JS code. If you want to select the generated code to use the ECMAScript 5, pass the
argument
```
--ecmascript=es5
```

In order to wrap the generated code into a template, pass the argument. 
```
--template=template.html
```
Write the expression **`{{code}}`** in the template to tell GenDOMan where to place the generated code. Example:

```html
<html>
    <head>
        <title>This is an example</title>
        <script>{{code}}</script>
    </head>
    <body></body>
</html>
```

If you want a node to be created dynamically with a foreach loop, use the attribute `data-repeat`,
with the value `element in collection`, being *element* the name of the iterating variable, and *collection*
 the collection that is iterated. Example:
 ```html
 ...
 <div data-repeat="node in [1, 2, 3, 4, 5]"></div>
 ...
 ```
 This will generate the following code:
 ```javascript
 // ...
 [1, 2, 3, 4, 5].forEach((node) => {
  var node1 = document.createElement("div");
  node0.appendChild(node2);
 }
 );
 // ...
 ```
