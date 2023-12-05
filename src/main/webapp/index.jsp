<%!
  String tableRowToHtml(TableRow tableRow) {
    if (tableRow == null) return "";
    return "<tr>" +
            "<td>" + tableRow.getX() + "</td>" +
            "<td>" + tableRow.getY() + "</td>" +
            "<td>" + tableRow.getR() + "</td>" +
            "<td>" + tableRow.getClientDate() + "</td>" +
            "<td>" + tableRow.getScriptWorkingTime() + " ms</td>" +
            "<td>" + tableRow.isHit() + "</td>" +
            "</tr>";
  }
%>


<%@ page import="org.rasul.labmaker.table.TableRow" %>
<jsp:useBean id="resultBean" class="org.rasul.labmaker.table.ResultBean" scope="application"/>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/main.css">
  <title>уэб 2</title>
</head>
<body>
<div id="container">
  <table>
    <thead height="200px">
    <tr>
      <th min-width="400px" max-width="400px">
        <h1 class="header">Bizhanov Rasul</h1>
        <h2 class="header">p3206 / 1743</h2>
      </th>

    </tr>
    </thead>
    <tbody>
    <tr>
      <td id="col1">
        <form id="form">
          <br>
          <div class="x-radio" >
            <label id="x-label"> X: </label>
            <input class="x" name="x" type="radio" value="-4" !checked /> <label>-4</label>
            <input class="x" name="x" type="radio" value="-3" !checked /> <label>-3</label>
            <input class="x" name="x" type="radio" value="-2" !checked /> <label>-2</label>
            <input class="x" name="x" type="radio" value="-1" !checked /> <label>-1</label>
            <input class="x" name="x" type="radio" value="0" !checked /> <label>0</label>
            <input class="x" name="x" type="radio" value="1" !checked /> <label>1</label>
            <input class="x" name="x" type="radio" value="2" !checked /> <label>2</label>
            <input class="x" name="x" type="radio" value="3" !checked /> <label>3</label>
            <input class="x" name="x" type="radio" value="4" !checked /> <label>4</label>
          </div>
          <br />

          <div class="y-input">
            <label id="y-label">Y: </label>
            <input id="y-value" name="y" type="text" placeholder="Y in range (-5.0, 5.0)">
          </div>
          <br/>

          <div class="r-checkbox" >
            <label id="r-label"> R: </label><br />
            <input class="r" name="r" type="checkbox" id="r1" value="1" !checked /> <label>1</label><br />
            <input class="r" name="r" type="checkbox" id="r2" value="2" !checked /> <label>2</label><br />
            <input class="r" name="r" type="checkbox" id="r3" value="3" !checked /> <label>3</label><br />
            <input class="r" name="r" type="checkbox" id="r4" value="4" !checked /> <label>4</label><br />
            <input class="r" name="r" type="checkbox" id="r5" value="5" !checked /> <label>5</label><br />
          </div>
          <br />

          <button type="submit" id="submit-button" disabled>Submit</button>
          <button type="clear" id="clear-button">Clear</button>
        </form>
      </td>
      <td id="col2">
        <center>
          <canvas id="graph_canvas" width="400" height="400"></canvas>
        </center>

      </td>
      <td id="col3">
        <div class = "result">
          <table id="results-table">
            <thead>
            <tr>
              <th width="5%">X</th>
              <th width="5%">Y</th>
              <th width="5%">R</th>
              <th width="45%">Current Time</th>
              <th width="20%">Script Time(ms)</th>
              <th width="20%">Result</th>
            </tr>
            </thead>
            <tbody id="results-content">
            </tbody>
          </table>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</div>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script src="js/main.js"></script>
<script src="js/graph.js"></script>
<script src="js/communication.js"></script>
<script src="js/validator.js"></script>
<script src="js/tableWorker.js"></script>

</body>
</html>