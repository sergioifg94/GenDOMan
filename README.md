# GenDOMan

GenDOMan is a tool that generates the JavaScript code to create the nodes of a given HTML code

## Usage

A simple example of usage is:

```
gendoman -i input.html
```

By default, GenDOMan generates ES5 JS code. If you want to select the generated code to use the ECMAScript 6, pass the
argument
```
--ecmascript=es6
```

In order to wrap the generated code into a template, pass the argument. 
```
--template=template.html
```
Write the expression ```${code}``` in the template to tell GenDOMan where to place the generated code. Example:

```
<html>
    <head>
        <title>This is an example</title>
        <script>${code}</script>
    </head>
    <body></body>
</html>
```
