package fr.byob.client.chat.util;

import java.util.ArrayList;
import java.util.Iterator;

import fr.byob.client.chat.model.ChatModel.EventHandler;

public class HandlerCollection <T extends EventHandler>{
  private static final ArrayList EMPTY_LIST = new ArrayList();
  
  private ArrayList<T> handlers;

  public void add(T handler) {
    handlers = (handlers == null) ? new ArrayList<T>() : new ArrayList<T>(handlers);
    handlers.add(handler);
  }

  public void remove(Object handler) {
    if (handlers == null || handler == null) {
      return;
    }

    Iterator<T> it = handlers.iterator();
    handlers = new ArrayList<T>();
    while (it.hasNext()) {
      T obj = it.next();
      if (obj != handler) {
        handlers.add(obj);
      }
    }
  }

  public Iterator<T> iterator() {
    return (handlers == null) ? EMPTY_LIST.iterator()
        : handlers.iterator();
  }

  public int size() {
    return (handlers == null) ? 0 : handlers.size();
  }
  
  public void clear() {
    handlers = null;
  }
}
