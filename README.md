# Jsonsmith

A java library for roughly handling JSON-like objects.

# Usage

## ObjectWrapper

For example,

``` java
Object obj =
    new ObjectBuilder()
        .put(".hello", "jsonsmith")
        .put(".items[0].key", 1)
        .put(".items[0].value", "item-0-value")
        .put(".items[2].key", 2)
        .put(".items[2].value", "item-1-value")
        .put(".set.very.deep.property.directly", true)
        .build();

ObjectMapper objectMapper = new ObjectMapper()
String json = objectMapper.writeValueAsString(obj);

System.out.println(json);
```

then, outputs below string.

``` json
{
  "hello" : "jsonsmith",
  "items" : [
    {
      "key" : 1,
      "value" : "item-0-value"
    },
    null,
    {
      "key" : 2,
      "value" : "item-1-value"
    }
  ],
  "set" : {
    "very" : {
      "deep" : {
        "property" : {
          "directly" : true
        }
      }
    }
  }
}
```
