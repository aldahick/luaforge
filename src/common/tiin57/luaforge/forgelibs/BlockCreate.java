package tiin57.luaforge.forgelibs;

import org.luaj.vm2.Varargs;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;

public class BlockCreate extends Block {
	public BlockCreate(Varargs args) {
		super((Integer) args.arg(1).toint(), (Integer) args.arg(2).toint(), (Material) ((Object)args.arg(3).tojstring()));
		Object[] obj = (new Object[] {
			args.arg(1).toint(),
			args.arg(2).toint(),
			args.arg(3).tojstring(),
			args.arg(4).tojstring(),
			args.arg(5).tofloat(),
			args.arg(6).tofloat()
		});
		int id = ((Integer) obj[0]);
		int iconIndex = ((Integer) obj[1]);
		Material material = ((Material) obj[2]);
		StepSound sound = ((StepSound) obj[3]);
		float hardness = (float) ((Integer) obj[4]);
		float resistance = (float) ((Integer) obj[5]);
		this.setStepSound(sound);
		this.setHardness(hardness);
		this.setResistance(resistance);
	}
}
