<!DOCTYPE html>
<html lang="en">
<head>
  <title>Onikai</title>
  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link href="https://fonts.googleapis.com/css2?family=Changa:wght@400;600&display=swap" rel="stylesheet">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <#include "shared/_og_image.ftlh">
  <#include "shared/_faveicon.ftlh">
  <link rel="stylesheet" href="/css/${content}.css" />
  <link rel="stylesheet" href="/css/application.css" />

  <#include "shared/_analytics.ftlh">
</head>
<body class="${content}">
  <#include "shared/_header.ftlh">
  <#include "${content}.ftlh">
  <#include "shared/_footer.ftlh">
</body>
</html>