
package acme.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.client.helpers.LoggerHelper;
import acme.entities.banners.Banner;

@ControllerAdvice
public class BannerAdvisor {

	// Internal state ---------------------------------------------------------

	@Autowired
	private BannerRepository repository;

	// Beans ------------------------------------------------------------------


	@ModelAttribute("banner")
	public Banner getBanner() {
		Banner result;

		try {
			result = this.repository.findRandomBanner();
		} catch (final Throwable oops) {
			LoggerHelper.error(oops.getLocalizedMessage());
			result = null;
		}

		return result;
	}

}
