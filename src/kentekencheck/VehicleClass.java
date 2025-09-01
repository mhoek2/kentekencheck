/*
 * This file is part of Kentekencheck.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package kentekencheck;

import com.google.gson.JsonObject;

/**
 * Represents a vehicle using data retrieved in JSON format.
 * <p>
 * This class provides access to vehicle information,
 * based on data stored in a {@link JsonObject}.
 * </p>
 */
public class VehicleClass {
	/** JSON object containing the vehicle data */
    JsonObject data;

    String kenteken;
    String merk;
    String model;
    String vervaldatum;
    
    /**
     * Constructs a VehicleClass object using the provided JSON data.
     *
     * @param data A {@link JsonObject} containing key-value pairs with details
     */
    VehicleClass( JsonObject data ) {
        this.data = data;
    }
    
    /**
     * Retrieves a value from the JSON data using the a key.
     * <p>
     * If the key does not exist or its value is null, an empty string is returned.
     * </p>
     *
     * @param key The key whose associated value is to be returned
     * @return The value as a {@link String}, or an empty string if the key is not found or null
     */
	public String getValue( String key )
	{
		try {
			return this.data.get( key ).getAsString();
		}
		catch ( NullPointerException e ) {
			return new String("");
		}
	}  
}
