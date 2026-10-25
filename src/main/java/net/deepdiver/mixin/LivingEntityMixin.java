package net.deepdiver.mixin;

import net.deepdiver.DivingSuit;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	public abstract void jumpFromGround();

	private LivingEntity deepdiver$self() {
		return (LivingEntity)(Object)this;
	}

	@Inject(method = "canBreatheUnderwater", at = @At("HEAD"), cancellable = true)
	private void deepdiver$breathe(CallbackInfoReturnable<Boolean> cir) {
		if (DivingSuit.hasHelmet(this.deepdiver$self())) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "shouldTravelInFluid", at = @At("HEAD"), cancellable = true)
	private void deepdiver$walkOnSeaFloor(FluidState fluid, CallbackInfoReturnable<Boolean> cir) {
		if (fluid.is(FluidTags.WATER) && DivingSuit.walksOnSeaFloor(this.deepdiver$self())) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "travel", at = @At("HEAD"))
	private void deepdiver$limitSink(Vec3 input, CallbackInfo ci) {
		LivingEntity self = this.deepdiver$self();
		if (self.isInWater() && DivingSuit.walksOnSeaFloor(self)) {
			Vec3 v = self.getDeltaMovement();
			if (v.y < DivingSuit.MAX_SINK_SPEED) {
				self.setDeltaMovement(v.x, DivingSuit.MAX_SINK_SPEED, v.z);
			}

			self.resetFallDistance();
		}
	}

	@Inject(method = "jumpInLiquid", at = @At("HEAD"), cancellable = true)
	private void deepdiver$jump(TagKey<Fluid> fluid, CallbackInfo ci) {
		LivingEntity self = this.deepdiver$self();
		if (!FluidTags.WATER.equals(fluid) || !DivingSuit.walksOnSeaFloor(self)) {
			return;
		}

		if (self.onGround()) {
			this.jumpFromGround();
		} else {
			Vec3 v = self.getDeltaMovement();
			self.setDeltaMovement(v.x, Math.max(v.y, DivingSuit.ASCENT_THRUST), v.z);
		}

		ci.cancel();
	}
}
