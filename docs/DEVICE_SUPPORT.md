# Device Support

## Initial reference target

| Property | Initial target |
|---|---|
| Manufacturer / model | Motorola Moto G 2025 |
| Model identifier | XT2513-1 |
| Platform | Android 16 |
| Firmware family | Stock Kansas |
| Root provider | KernelSU Next |
| SELinux | Enforcing |
| Boot state | Preserve the existing locked stock boot posture |

These values define the intended development target. Runtime code must read and verify device properties rather than blindly trusting this document.

## Support tiers

- **Reference-tested:** the exact device/build combination has passed the documented test suite.
- **Capability-compatible:** required capabilities are detected, but full device validation is incomplete.
- **Experimental:** code may be exercised only in a controlled test environment.
- **Unsupported:** a required capability is absent, a target is ambiguous, or the safety preconditions cannot be established.

Do not label a build reference-tested until the exact tested build fingerprint, kernel version, root-provider version, test results, and known limitations are recorded.

## Required read-only inventory

The first milestone should collect, where available:
- Manufacturer, model, product/device identifiers, build fingerprint, Android release and API level.
- ABI, kernel release, boot state, and SELinux mode.
- Root provider name/version and supported capabilities.
- Installed root modules, their identifiers, versions, enabled/disabled state, and declared conflicts.
- Mount namespace and font-target discovery results needed by enabled features.
- Availability and access mode of candidate tuning interfaces, without writing to them.

Avoid collecting account identifiers, private application data, tokens, full logs, or unrelated personal files.

## Compatibility rules

- Detect capabilities at runtime; do not assume all Android 16 devices behave like this Moto G.
- Unknown or untested interfaces must be read-only.
- Never guess sysfs paths, valid ranges, mount semantics, or rollback procedures.
- Distinguish unavailable, denied, unsupported, and failed checks.
- Preserve the stock Motorola/Kansas identity.
- Keep Advanced Account Protection and the existing boot/security posture outside the app's mutation scope.
- Do not claim that root access overrides Android platform restrictions or guarantees a successful operation.

## Validation before device rollout

1. Unit tests for parsing, validation, plan generation, conflict handling, and rollback state.
2. Static checks and reproducible build.
3. Emulator or disposable test device for UI and non-hardware-dependent behavior.
4. Test device with the same Android major version and root provider for privileged integration.
5. Moto G 2025 canary testing with backups and a documented recovery route.
6. Publish tested build identifiers, observed results, and known limitations.
