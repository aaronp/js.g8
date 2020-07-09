A [Giter8](http://www.foundweekends.org/giter8/index.html) template for a single project with typical plugins, publish options, etc

## Usage

```bash
sbt new aaronp/js.g8
```

Which creates a scalajs project where you can then run:
```
sbt makePage
```


### Testing locally

You need g8 (e.g. "brew install g8"), but then just run 

```
./test.sh
```
which should open an sbt project in your new 'localtest' project.
