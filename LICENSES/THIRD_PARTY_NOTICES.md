# Third-party notices and provenance

This file tracks projects under evaluation. Listing a project does not mean its source code, binaries, or assets have been copied into this repository.

Before importing any component, record the exact upstream URL, commit/tag, files copied or adapted, copyright holders, applicable license, required notices, modifications, and any redistribution conditions.

## aOpenFont

- Repository: https://github.com/Gabe284/aOpenFont
- Intended review: font management, Hack font configuration, validation, and architecture.
- Import status: not yet imported into this repository.
- License review: verify the repository license and the licenses of any bundled font assets before copying or redistributing them.

## ZeroMount

- Repository: https://github.com/Enginex0/zeromount
- Intended review: emoji target discovery, mount implementation, configuration, and diagnostics.
- Import status: not yet imported into this repository.
- License review: record the exact upstream revision and preserve applicable notices. Audit scripts for destructive conflict handling before adapting them.

## FancyMoji

- Repository: https://github.com/newhertz/FancyMoji
- Intended review: emoji source selection and user-interface patterns.
- Import status: not yet imported into this repository.
- License review: inspect the exact release/source license. Verify each bundled font asset's redistribution license independently; do not assume the repository code license covers the fonts.

## KernelSU Next

- Repository: https://github.com/KernelSU-Next/KernelSU-Next
- Intended use: root-provider integration through supported interfaces.
- Import status: no KernelSU Next code is currently imported or bundled.
- License review: review applicable upstream terms before any code integration or redistribution.

## Required record for each imported component

| Field | Required information |
|---|---|
| Component | Name and purpose |
| Upstream | Canonical URL |
| Revision | Exact commit or release tag |
| Files | Paths copied, adapted, or generated |
| Copyright | Original notices and authors |
| License | Exact license and relevant obligations |
| Assets | Separate font/image/binary asset license |
| Changes | Summary of modifications |
| Tests | Validation performed in this project |

## Project license

The project-wide license has not yet been selected. Until it is selected, do not assume that files without explicit provenance are cleared for redistribution. Keep third-party components separable and retain their original license terms.
