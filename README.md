A [Giter8](http://www.foundweekends.org/giter8/index.html) template for a single project with typical plugins, publish options, etc

## Usage

```bash
sbt new aaronp/js.g8
```

### Testing locally

You need 'brew install g8', but then 
```bash
 g8 file://. --name=localtest --force
 cd localtest
 sbt test
```