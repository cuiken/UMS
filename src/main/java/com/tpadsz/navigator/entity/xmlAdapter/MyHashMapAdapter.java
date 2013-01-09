package com.tpadsz.navigator.entity.xmlAdapter;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

final public class MyHashMapAdapter extends
		XmlAdapter<MyHashMapType, HashMap<String, String>> {

	@Override
	public MyHashMapType marshal(HashMap<String, String> arg0) throws Exception {
		MyHashMapType myHashMapType = new MyHashMapType();
		for (String key : arg0.keySet()) {
			MyHashEntryType myHashEntryType = new MyHashEntryType();
			myHashEntryType.key = key;
			myHashEntryType.value = (String) arg0.get(key);
			myHashMapType.entries.add(myHashEntryType);
			// myHashMapType = myHashEntryType;
		}
		return myHashMapType;
	}

	@Override
	public HashMap<String, String> unmarshal(MyHashMapType arg0)
			throws Exception {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (MyHashEntryType myHashEntryType : (List<MyHashEntryType>) arg0.entries) {
			hashMap.put(myHashEntryType.key, myHashEntryType.value);
		}
		return hashMap;
	}

}
