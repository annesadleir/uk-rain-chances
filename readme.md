# UK Rain Chances
A lambda for Alexa

### Fetches short-term rain probability information
My mum says the Met Office's near-term rain probability information is great for someone who doesn't want to get rained on when she nips out to feed the alpacas.

This lambda gives a summary of the highest and lowest chances of rain in a specific place for any time period between now and the end of tomorrow.

### Data source
The Met Office Datapoint API only gives 3-hourly data, which isn't good enough for my purposes.

Therefore this scrapes data from the lovely Met Office mobile website (using Jsoup)

It has some locations hard-coded but otherwise it looks for a location with the same name.

### Designed to be a lambda
Has two separate entry points:
1. the RainChancesHandler which implements RequestStreamHandler for standard lambda deployment: expects a Json RainQuery in format
`{"location":"location","nextHours":8,"start":"2017-06-15T10:00","end":"2017-06-15T14:00"}`
2. the RainChanceSpeechletRequestStreamHandler for use in Alexa.

#### Alexa set-up
For Alexa use the second handler as the Lambda's handler class.

The environment variable `APP_ID` should be set to the Alexa app id.

The Alexa invocation name needs to be hard-coded in the RainChancesControllerSupplier class, or else the welcome message won't make sense.

You will need to set the Trigger to be Alexa Skills Kit, and publish a version of the lambda as Alexa does not seem to like `$LATEST`

The sample utterances I have come up with are:
* {date} between {start} and {end} in {location}
* {date} in {location} between {start} and {end}
* {location} {date} between {start} and {end}
* the next {hours} hours in {location}
* {location} for the next {hours} hours

where the slot types are:
* location: AMAZON.GB_CITY
* start: AMAZON.TIME
* end: AMAZON.TIME
* date: AMAZON.DATE
* hours: AMAZON.NUMBER
