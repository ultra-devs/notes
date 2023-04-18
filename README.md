# notes

### EXCEL FORMULA
Select "Custom" from the Category list.
In the "Type" box, enter the following code: "$#,##0.00;($#,##0.00)"

Select "Custom" from the Category list.
In the "Type" box, enter the following code: "$#,##0.00" & REPT(" ", 10-LEN(TEXT(VALUE(A1),"$#,##0.00")))) & TEXT(VALUE(A1),"$#,##0.00")"
Change the number "10" in the formula to the maximum number of digits that you expect your number to have. For example, if your maximum number is 1000000, use "7" instead of "10" (because "1000000" has seven digits).
