# ParametricCurveGrapher

This program traces out parametric curves in two dimensions. A parametric curve is a set 
of points in the xy-plane whose coordinates (x, y) are specified in terms of a third
variable, t. For any arbitrary t value, the x-coordinate is given by the function x(t), and
the y-coordinate is likewise given by the function y(t). The equations for x(t) and y(t)
are called the parametric equations of the curve.

Some screenshots of the program in action are provided below.

This first screenshot shows the [Butterfly Curve](https://en.wikipedia.org/wiki/Butterfly_curve_(transcendental)) being traced out.

![Picture of the user interface](https://user-images.githubusercontent.com/25561432/82864687-f264b700-9ed9-11ea-8a92-c14f2485e2a2.PNG)

Shown below is a screenshot of a parametric curve with a beautiful oscillatory pattern.

![A picture of the user interface with a different graph](https://user-images.githubusercontent.com/25561432/82864681-f09af380-9ed9-11ea-8ec0-5aae41c25822.PNG)


### Usage

To graph a parametric curve using this program, fill in the parametric equations for x(t)
and y(t) along with the bounds of the t variable in the appropriate text fields. Then, click the
"Draw curve!" button.

This program currently accepts parametric equations containing the variable t, real numbers, 
operators (+, -, *, /, ^), parentheses, and the trigonometric functions sin, cos, and tan. Constants 
like e and pi, as well as other functions like ln and arcsin, are not currently supported.
