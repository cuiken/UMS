package com.tpadsz.navigator;

import java.util.List;
import java.util.Map;

import com.tpadsz.navigator.entity.Bottom;
import com.tpadsz.navigator.entity.Button;
import com.tpadsz.navigator.entity.CenterLeft;
import com.tpadsz.navigator.entity.CenterRight;
import com.tpadsz.navigator.entity.Top;

public interface IButtonSourceAdapter {

	abstract public Button getNewsButton(String template);

	abstract public Button getShoppingButton(String template);

	abstract public Button getTravelingButton(String template);

	abstract public Button getReadingButton(String template);

	abstract public Map<Button, Integer> getAllNewsButtonClicks(String userId,
			Long timeLimit);

	abstract public List<Button> getRandom4News();

	abstract public List<Button> getRandom4Shopping();

	abstract public List<Button> getRandom4Traveling();

	abstract public List<Button> getRandom4Reading();

	abstract public List<Button> getDefaultNews();

	abstract public List<Button> getDefaultShopping();

	abstract public List<Button> getDefaultBottom();

	abstract public List<Button> getDefaultReading();

	abstract public boolean isShoppingHotterThanTraveling(String userId);

	abstract public List<Button> getBottom(String userId, Long timeLimit);

	abstract public List<Button> getRandomBottom();

	abstract public Map<Button, Integer> getAllShoppingButtonClicks(
			String userId, Long timeLimit);

	abstract public Map<Button, Integer> getAllTravelingButtonClicks(
			String userId, Long timeLimit);

	abstract public Map<Button, Integer> getAllReadingButtonClicks(
			String userId, Long timeLimit);

	abstract public Map<Button, Integer> getAllClicksOfClass(Integer classId,
			String userId, Long timeLimit);

	abstract public void logClick(Map<String, String> clientParams,
			Long buttonId);

	abstract public Button getMostRecentButtonOfCategory(String uuid,
			String uuidException);

}
