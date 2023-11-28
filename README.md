### elevation test data

-56.0582, -157.7986 (elevation: 2 meters)
-37.7428, 178.5662 (elevation: 2 meters)
-52.0035, 172.9375 (elevation: 3 meters)
29.1897, -94.9761 (elevation: 3 meters)
-71.0662, 122.9924 (elevation: 4 meters)
42.9844, 141.5086 (elevation: 4 meters)
-45.5118, 166.6423 (elevation: 1 meter)
60.4355, -166.3441 (elevation: 1 meter)
-67.8636, -141.3506 (elevation: 1 meter)
-77.4375, 162.3004 (elevation: 1 meter)
16.1580, -7.2220 (elevation: 10 meters)
-28.9573, 32.5360 (elevation: 9 meters)
-15.7829, 35.0112 (elevation: 9 meters)
6.2294, 10.7862 (elevation: 6 meters)
22.4061, 91.9788 (elevation: 5 meters)
46.9785, -123.8206 (elevation: 5 meters)
55.0515, -162.9431 (elevation: 5 meters)
-5.7880, -35.8413 (elevation: 5 meters)
-48.1559, -123.1102 (elevation: 12 meters)
34.4654, 132.7853 (elevation: 12 meters)
51.5406, 173.0155 (elevation: 2 meters)
-63.5207, 146.4087 (elevation: 4 meters)
70.4693, -162.5249 (elevation: 4 meters)
75.1605, -123.3845 (elevation: 4 meters)
-29.1897, 94.9761 (elevation: 0 meters)
20.6558, -105.2259 (elevation: 7 meters)
-22.9765, -44.0485 (elevation: 5 meters)
-32.3571, 115.7404 (elevation: 2 meters)
7.9654, -11.7169 (elevation: 7 meters)
-17.7345, -149.2402 (elevation: 4 meters)
27.7286, -82.4319 (elevation: 1 meter)
-7.2595, -34.8417 (elevation: 10 meters)
-8.7167, 115.1833 (elevation: 100 meters)
9.0982, -79.7143 (elevation: 3 meters)
-23.9706, -46.3237 (elevation: 22 meters)
35.3733, 140.3808 (elevation: 6 meters)
38.0908, -122.7695 (elevation: 7 meters)
56.4072, -133.4226 (elevation: 2 meters)
-10.1819, 123.5838 (elevation: 27 meters)
-12.7939, -38.5117 (elevation: 10 meters)
29.5944, -90.7195 (elevation: 0 meters)
44.3205, -68.7831 (elevation: 11 meters)
60.9350, -149.1393 (elevation: 8 meters)
-4.3023, 152.4382 (elevation: 23 meters)
41.4699, -82.7102 (elevation: 2 meters)
-8.7148, 115.1676 (elevation: 94 meters)
40.8190, -73.9501 (elevation: 6 meters)
-33.7799, 151.2964 (elevation: 3 meters)
42.1912, -70.7387 (elevation: 9 meters)
37.7920, -122.3933 (elevation: 5 meters)
https://chat.openai.com/share/122b4d5f-bbb5-48d1-b8ef-c711bfa7d03c


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>File Diff Viewer</title>
  <style>
    .removed {
      background-color: #ffcccc; /* Red for removal */
    }
    .updated {
      background-color: #ffffcc; /* Yellow for updated */
    }
  </style>
</head>
<body>
  <input type="file" id="fileInput1">
  <input type="file" id="fileInput2">
  <button onclick="compareFiles()">Compare</button>
  <pre id="diffViewer"></pre>

  <script>
    function compareFiles() {
      var fileInput1 = document.getElementById('fileInput1');
      var fileInput2 = document.getElementById('fileInput2');
      var diffViewer = document.getElementById('diffViewer');

      var file1 = fileInput1.files[0];
      var file2 = fileInput2.files[0];

      var reader1 = new FileReader();
      var reader2 = new FileReader();

      reader1.onload = function(event) {
        var text1 = event.target.result;

        reader2.onload = function(event) {
          var text2 = event.target.result;

          var lines1 = text1.split('\n');
          var lines2 = text2.split('\n');

          var output = '';

          for (var i = 0; i < Math.max(lines1.length, lines2.length); i++) {
            if (lines1[i] !== lines2[i]) {
              if (lines1[i] === undefined) {
                output += '<div class="removed">' + lines2[i] + '</div>';
              } else if (lines2[i] === undefined) {
                output += '<div class="updated">' + lines1[i] + '</div>';
              } else {
                output +=
                  '<div class="removed">' +
                  lines1[i] +
                  '</div><div class="updated">' +
                  lines2[i] +
                  '</div>';
              }
            } else {
              output += '<div>' + lines1[i] + '</div>';
            }
          }

          diffViewer.innerHTML = output;
        };

        reader2.readAsText(file2);
      };

      reader1.readAsText(file1);
    }
  </script>
</body>
</html>
