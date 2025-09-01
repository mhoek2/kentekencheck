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

public class VehicleClass {
    JsonObject data;
    String kenteken;
    String merk;
    String model;
    String vervaldatum;
    
    VehicleClass( JsonObject data ) {
        this.data = data;
    }
    
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
