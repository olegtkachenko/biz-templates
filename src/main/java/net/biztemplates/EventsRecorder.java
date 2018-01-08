/*
 *    Copyright (c) 2017, The EDUARD BURENKOV (http://edevapps.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.biztemplates;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;


public class EventsRecorder {

  private ConcurrentLinkedDeque<Event> record = new ConcurrentLinkedDeque<>();
  private boolean isRecord;

  protected ConcurrentLinkedDeque<Event> getRecord() {
    return record;
  }

  public Observer observer() {
    return new EventObserver();
  }

  public boolean isEmpty() {
    return record.isEmpty();
  }

  public boolean isRecord() {
    return isRecord;
  }

  public void startRecord() {
    if (this.isRecord) {
      return;
    }

    synchronized (record) {
      this.record.clear();
      this.isRecord = true;
    }
  }

  public void stopRecord() {
    if (!this.isRecord) {
      return;
    }

    synchronized (record) {
      this.isRecord = false;
    }
  }

  public Event[] eventsChain() {
    if (this.isRecord) {
      return new Event[0];
    }

    return this.record.toArray(new Event[record.size()]);
  }

  public void clearEvents() {
    this.record.clear();
  }

  private class EventObserver implements Observer {

    @Override
    public void handleEvent(String name, Object args) throws IllegalArgumentException {
      if (isRecord) {
        record.add(new Event(name, args));
      }
    }

    @Override
    public void handleEvent(String name) throws IllegalArgumentException {
      if (isRecord) {
        record.add(new Event(name, null));
      }
    }

    @Override
    public String[] events() {
      return Arrays.stream(eventsChain()).map(Event::getEvent).toArray(String[]::new);
    }
  }
}
