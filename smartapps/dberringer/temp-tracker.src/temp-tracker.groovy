/**
 *  Temp Tracker
 *
 *  Copyright 2016 Doug
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Temp Tracker",
    namespace: "dberringer",
    author: "Doug",
    description: "Temperature tracking and deltas.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Log Temperature") {
		// TODO: put inputs here
		//input "temps", "capability.temperatureMeasurement", title: "Temp. Sensors", required: true, multiple: true
		input "outdoor", "capability.temperatureMeasurement", title: "Outdoor Temp Sensor", required: true, multiple: false
		input "indoor", "capability.temperatureMeasurement", title: "Indoor Temp Sensor", required: true, multiple: false
		input "roof", "capability.temperatureMeasurement", title: "Roof Temp Sensor", required: true, multiple: false
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    log.debug "Initialized"
    
    subscribe(outdoor, "temperature", myHandler)
    subscribe(indoor, "temperature", myHandler)
    subscribe(roof, "temperature", myHandler)
    
    def startDate = new Date() - 3
    def endDate = new Date()
    def outdoorEvents = outdoor.eventsBetween(startDate, endDate);
    log.debug "outdoor events: ${outdoorEvents}"
    
    for (int i = 0; i < outdoorEvents.size(); i++) {
        myHandler(outdoorEvents[i], i)
        log.debug "done: ${i}"
		Thread.sleep(4000)
    }
}

// TODO: implement event handlers

def myHandler(evt, i) {
  log.debug "count: ${i}"
  log.debug "Device: ${evt.displayName}"
  log.debug "Date: ${evt.date}"
  log.debug "Temperature: ${evt.value} deg. F"
}

